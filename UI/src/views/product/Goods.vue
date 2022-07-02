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
            <el-table-column prop="id" label="#ID" sortable align="center" width="80"></el-table-column>
            <el-table-column prop="name" label="品类名称" sortable align="center"></el-table-column>
            <el-table-column prop="statusX" label="状态" sortable align="center" width="80"></el-table-column>
            <el-table-column prop="updateBy" label="最后一次更新者" sortable align="center"></el-table-column>
            <el-table-column prop="updateTime" label="最后一次更新时间" sortable align="center" :formatter="dateFormatter"></el-table-column>
        </el-table>
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

const route = useRoute();
const router = useRouter();

const tableData = ref([])

const dateFormatter = (row: any) => {
    return dayjs(row.updateTime).format('YYYY-MM-DD HH:mm:ss');
}

onMounted(() => {
    http
        .post("/api/goods/page", {
        })
        .then((res: any) => {
            console.log(res);
            tableData.value = res
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
})

const addClick = () => {
    setTimeout(() => {
        router.push({name: "goodsadd"}), 1000
    })
}
</script>