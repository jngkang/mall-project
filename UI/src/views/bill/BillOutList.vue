<template>
    <div>
        <div style="text-align: center;">
            <el-form :inline="true" :model="queryForm" class="demo-form-inline">
                <el-form-item style="width: 35%;">
                    <el-input v-model="queryForm.query" clearable @clear="tableDataInit"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="tableDataInit">查询</el-button>
                </el-form-item>
            </el-form>
        </div>
        <el-table
                :data="tableData"
                style="width: 100%; margin-bottom: 20px; margin-top: 20px;"
                stripe="true"
                row-key="id"
                border
        >
            <el-table-column prop="id" label="#ID" align="center" width="80" fixed="left"></el-table-column>
            <el-table-column prop="billNo" label="单据编号" align="center" fixed="left"></el-table-column>
            <el-table-column prop="vendorName" label="供应商" align="center"></el-table-column>
            <el-table-column prop="billDate" label="出库日期" align="center" width="180" :formatter="dateFormatter"></el-table-column>
            <el-table-column prop="lastUpdateBy" label="最后一次更新者" align="center" width="180"></el-table-column>
            <el-table-column prop="lastUpdateTime" label="最后一次更新时间" align="center" width="180" :formatter="dateFormatter"></el-table-column>
            <el-table-column label="操作" align="center" width="80" fixed="right">
                <template #default="scope">
                    <el-tooltip class="box-item" content="查看入库商品" placement="top">
                        <el-button :icon="Document" circle @click="openInfo(scope.row)"></el-button>
                    </el-tooltip>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <div style="text-align: center;">
        <div style="display: inline-block; margin-top: 20px;">
            <el-pagination background
                           layout="total, sizes, prev, pager, next, jumper"
                           :page-sizes="[5, 10, 20, 50, 100]"
                           :total="total"
                           v-model:currentPage="currentPage"
                           v-model:page-size="pageSize"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
            ></el-pagination>
        </div>
    </div>
    <div>
        <el-drawer v-model="itemsListDrawer"
                   direction="ltr"
                   size="45%"
                   :before-close="handleClose"
        >
            <template #title>
                <h4>单据详细信息</h4>
            </template>
            <template #default>
                <div>
                    <el-table
                            :data="billItemData"
                            style="width: 100%; margin-bottom: 20px; margin-top: 20px;"
                            stripe="true"
                            row-key="id"
                            border
                    >
                        <el-table-column prop="productId" label="商品ID" align="center"></el-table-column>
                        <el-table-column prop="productName" label="商品名称" align="center"></el-table-column>
                        <el-table-column label="图片" align="center">
                            <template #default="scope">
                                <el-image
                                        style="width: 60px; height: 60px"
                                        :src="scope.row.productImg"
                                        :preview-src-list="[scope.row.productImg]"
                                        fit="cover"
                                        preview-teleported="true"
                                ></el-image>
                            </template>
                        </el-table-column>
                        <el-table-column prop="qty" label="数量" sortable align="center"></el-table-column>
                    </el-table>
                </div>
            </template>
            <template #footer>
                <div style="flex: auto; text-align: center;">
                    <el-button @click="handleClose">取消</el-button>
                </div>
            </template>
        </el-drawer>
    </div>
</template>

<script lang="ts" setup>
import {ref, reactive, onMounted, defineExpose} from "vue";
import {ElMessageBox} from 'element-plus'
import http from "@/http/index";
import type {UploadInstance} from "element-plus";
import {ElMessage} from "element-plus/es";
import dayjs from 'dayjs'
import {Delete, Edit, QuestionFilled, Document} from '@element-plus/icons-vue'
import {CodeToText} from 'element-china-area-data'

const itemsListDrawer = ref(false)

const tableData = ref([])
const billItemData = ref([])
// 总数据量
const total = ref(0)
// 一页数据量
const pageSize = ref(5)
// 页码
const currentPage = ref(1);

const queryForm = reactive({
    query: "",
});

const dateFormatter = (row: any) => {
    return dayjs(row.updateTime).format('YYYY-MM-DD HH:mm:ss');
}

onMounted(() => {
    tableDataInit()
})

const handleSizeChange = () => {
    tableDataInit();
}

const handleCurrentChange = () => {
    tableDataInit();
}

const tableDataInit = () => {
    http
        .post("/api/billIn/select", {
            billNo: queryForm.query + "",
            pageSize: pageSize.value,
            pageNum: currentPage.value,
        })
        .then((res: any) => {
            total.value = res.total
            tableData.value = res.items
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
}

const openInfo = (row) => {
    billItemData.value = []
    http
        .post("/api/billInItem/select", {
            billId: row.id,
        })
        .then((res: any) => {
            billItemData.value = res
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败" + err)
        });
    itemsListDrawer.value = true
}

const handleClose = () => {
    itemsListDrawer.value = false
}

</script>