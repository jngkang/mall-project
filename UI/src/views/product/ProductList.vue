<template>
    <div>
        <el-button type="primary" @click="addClick">添加</el-button>
        <el-table
                :data="tableData"
                style="width: 100%; margin-bottom: 20px; margin-top: 20px;"
                stripe="true"
                row-key="id"
                border
        >
            <el-table-column prop="id" label="#ID" align="center" width="80"></el-table-column>
            <el-table-column prop="name" label="商品名称" align="center"></el-table-column>
            <el-table-column prop="categoryName" label="类别" sortable align="center"></el-table-column>
            <el-table-column label="图片" align="center">
                <template #default="scope">
                    <el-image
                            style="width: 60px; height: 60px"
                            :src="scope.row.img"
                            :preview-src-list="[scope.row.img]"
                            fit="cover"
                            preview-teleported="true"
                    ></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="price" label="价格" sortable align="center"></el-table-column>
            <el-table-column prop="statusX" label="状态" align="center" width="80"></el-table-column>
            <el-table-column prop="lastUpdateBy" label="最后一次更新者" align="center"></el-table-column>
            <el-table-column prop="lastUpdateTime" label="最后一次更新时间" align="center" :formatter="dateFormatter"></el-table-column>
            <el-table-column label="操作" align="center" width="150">
                <template #default="scope">
                    <el-tooltip class="box-item" content="查看商品信息" placement="top">
                        <el-button :icon="Document" circle></el-button>
                    </el-tooltip>
                    <el-tooltip class="box-item" content="编辑" placement="top">
                        <el-button type="primary" :icon="Edit" circle @click.prevent="openEditView(scope.row)"></el-button>
                    </el-tooltip>
                    <el-tooltip class="box-item" content="删除" placement="top">
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
</template>

<script lang="ts" setup>
import {ref, reactive, onMounted} from "vue";
import {ElMessageBox} from 'element-plus'
import http from "@/http/index";
import type {UploadInstance} from "element-plus";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus/es";
import dayjs from 'dayjs'
import {Delete, Edit, QuestionFilled, Document} from '@element-plus/icons-vue'

const route = useRoute();
const router = useRouter();

const tableData = ref([])

// 总数据量
const total = ref(0)
// 一页数据量
const pageSize = ref(5)
// 页码
const currentPage = ref(1);

const dateFormatter = (row: any) => {
    return dayjs(row.updateTime).format('YYYY-MM-DD HH:mm:ss');
}

const handleSizeChange = () => {
    tableDataInit();
}

const handleCurrentChange = () => {
    tableDataInit();
}

const softDelete = (row) => {
    http
        .post("/api/product/status/update", {
            id: row.id,
            status: 0,
        })
        .then((res: any) => {
            if (res === 'ok') {
                ElMessage.success("删除成功")
                tableDataInit()
            }
        })
        .catch((err: any) => {
            ElMessage.error(err)
        });
}

onMounted(() => {
    tableDataInit()
})

const tableDataInit = () => {
    http
        .post("/api/product/page", {
            pageSize: pageSize.value,
            pageNum: currentPage.value,
        })
        .then((res: any) => {
            console.log(res);
            total.value = res.total
            tableData.value = res.items
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
}

const addClick = () => {
    setTimeout(() => {
        router.push({name: "productadd"}), 1000
    })
}
</script>