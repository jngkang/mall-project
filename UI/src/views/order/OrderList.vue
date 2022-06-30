<template>
    <div>
        <el-table :data="tableData" style="width: 100%;">
            <el-table-column prop="id" label="#ID" width="180" align="center"></el-table-column>
            <el-table-column prop="receiver" label="收货人" width="180" align="center"></el-table-column>
            <el-table-column prop="phoneNumber" label="手机号" align="center"></el-table-column>
            <el-table-column prop="address" label="地址" align="center"></el-table-column>
        </el-table>
        <div style="text-align: center;">
            <div style="display: inline-block; margin-top: 20px;">
                <el-pagination background
                               layout="total, sizes, prev, pager, next, jumper"
                               :page-sizes="[1, 10, 20, 50, 100]"
                               :total="total"
                               v-model:currentPage="currentPage"
                               v-model:page-size="pageSize"
                               @size-change="handleSizeChange"
                               @current-change="handleCurrentChange"
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>

import {onMounted, ref} from "vue"
import http from "@/http/index";

// 总数据量
const total = ref(0)
// 一页数据量
const pageSize = ref(10)
// 页码
const currentPage = ref(1);
// 表格中的数据
const tableData = ref([])

const callApi = () => {
    http
        .post("/api/ordermaster/page", {
            pageNum: currentPage.value,
            pageSize: pageSize.value
        })
        .then((res: any) => { //response
            // console.log(res);
            tableData.value = res.items;
            total.value = res.total;
        })
        .catch((err: any) => {
        });
}

onMounted(() => {
    callApi();
})

const handleSizeChange = () => {
    callApi();
}

const handleCurrentChange = () => {
    callApi();
}
</script>
