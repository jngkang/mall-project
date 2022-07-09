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
        <el-button type="primary" @click="addClick">添加</el-button>
        <el-table
                :data="tableData"
                style="width: 100%; margin-bottom: 20px; margin-top: 20px;"
                stripe="true"
                row-key="id"
                border
        >
            <el-table-column prop="id" label="#ID" align="center" width="80" fixed="left"></el-table-column>
            <el-table-column prop="name" label="供应商名称" align="center" fixed="left"></el-table-column>
            <el-table-column prop="address" label="详细地址" align="center" width="300"></el-table-column>
            <el-table-column prop="statusX" label="状态" align="center" width="80"></el-table-column>
            <el-table-column prop="lastUpdateBy" label="最后一次更新者" align="center" width="180"></el-table-column>
            <el-table-column prop="lastUpdateTime" label="最后一次更新时间" align="center" width="180" :formatter="dateFormatter"></el-table-column>
            <el-table-column label="操作" align="center" width="150" fixed="right">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" circle @click.prevent="openEditView(scope.row)"></el-button>
                    <el-popconfirm
                            confirm-button-text="确定"
                            cancel-button-text="取消"
                            :icon="QuestionFilled"
                            icon-color="#ff0000"
                            title="确定要删除吗？"
                            @confirm="softDelete(scope.row)"
                    >
                        <template #reference>
                            <el-button type="danger" :icon="Delete" circle></el-button>
                        </template>
                    </el-popconfirm>
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
        <add-view ref="addViewRef"></add-view>
        <edit-view ref="editViewRef"></edit-view>
    </div>
</template>

<script lang="ts" setup>
import AddView from '@/views/vendor/Add.vue'
import EditView from '@/views/vendor/Edit.vue'
import {ref, reactive, onMounted, defineExpose} from "vue";
import {ElMessageBox} from 'element-plus'
import http from "@/http/index";
import type {UploadInstance} from "element-plus";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus/es";
import dayjs from 'dayjs'
import {Delete, Edit, QuestionFilled, Document} from '@element-plus/icons-vue'
import {CodeToText} from 'element-china-area-data'

const route = useRoute();
const router = useRouter();

const tableData = ref([])

const addViewRef = ref()
const editViewRef = ref()
const info = ref('')

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

const softDelete = (row) => {
    http
        .post("/api/vendor/status/update", {
            id: row.id,
            status: 0,
        })
        .then((res: any) => {
            if (res === 1) {
                ElMessage.success("删除成功")
                tableDataInit()
            }
        })
        .catch((err: any) => {
            ElMessage.error(err)
        });
}

const tableDataInit = () => {
    http
        .post("/api/vendor/select", {
            name: queryForm.query,
            pageSize: pageSize.value,
            pageNum: currentPage.value,
        })
        .then((res: any) => {
            total.value = res.total
            tableData.value = res.items
            tableData.value.forEach(res => {
                let temp = CodeToText[res.province] + " / " + CodeToText[res.city] + " / " + CodeToText[res.district] + " / " + res.address
                res.address = temp
            })
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
}

const openEditView = (row) => {
    editViewRef.value.getById(row)
    editViewRef.value.editDrawer = true;
}

const addClick = () => {
    addViewRef.value.addDrawer = true;
}

defineExpose({tableDataInit});
</script>