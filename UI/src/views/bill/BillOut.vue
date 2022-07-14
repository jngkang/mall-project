<template>
    <div>
        <div style="margin-bottom: 18px; display: flex; align-items: center; justify-content: space-between;">
            <div style="display: flex; align-items: center;">
                <span>出库时间：</span>
                <el-date-picker
                        v-model="billDate"
                        type="datetime"
                        clearable
                        style="margin-right: 10px;"
                ></el-date-picker>
                <el-button type="primary" @click="openProductList">选择商品</el-button>
            </div>
            <el-button type="primary" style="align-self: flex-end;" @click="confirmClick">出库</el-button>
        </div>
        <el-table
                :data="tableData"
                style="width: 100%; margin-bottom: 10px;"
                stripe="true"
                row-key="id"
                border
        >
            <el-table-column prop="id" label="商品ID" align="center" width="80"></el-table-column>
            <el-table-column prop="name" label="商品名称" align="center"></el-table-column>
            <el-table-column label="商品图片" align="center" width="100">
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
            <el-table-column prop="qty" label="入库数量" align="center" width="190">
                <template #default="scope">
                    <el-input-number v-model="scope.row.qty" min="1" :max="scope.row.qtyt"></el-input-number>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="80">
                <template #default="scope">
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

    <div>
        <BillOutListDrawer ref="BillOutListDrawerRef"></BillOutListDrawer>
    </div>
</template>

<script lang="ts" setup>
import BillOutListDrawer from './BillOutListDrawer.vue'
import {defineExpose, onMounted, ref} from "vue";
import {ElMessageBox} from 'element-plus'
import http from "@/http/index";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus/es";
import {Delete} from '@element-plus/icons-vue'
import appStore from "@/store/appStore";
import {storeToRefs} from "pinia";

let {products} = storeToRefs(appStore());

const route = useRoute();
const router = useRouter();

const vendorSelectData = ref([])

const tableData = ref([])
const billDate = ref(new Date())

const BillOutListDrawerRef = ref()

onMounted(() => {
    vendorSelectDataInit()
    tableData.value = products.value
})

const vendorSelectDataInit = () => {
    http
        .post("/api/vendor/select", {})
        .then((res: any) => {
            res.forEach(r => {
                vendorSelectData.value.push({value: r.id, label: r.name})
            })
        })
        .catch((err: any) => {
            ElMessage.error("供应商信息查询失败" + err)
        });
}

const openProductList = () => {
    BillOutListDrawerRef.value.productListDrawer = true;
    BillOutListDrawerRef.value.openInit();
}

const softDelete = (row) => {
    tableData.value.splice(tableData.value.indexOf(row), 1)
}

const confirmClick = () => {
    http
        .post("/api/billOut/insert", {
            billDate: billDate.value,
            billItemList: tableData.value,
        })
        .then((res: any) => {
            if (res === 1) {
                ElMessage.success("提交成功")
                reset()
            }
        })
        .catch((err: any) => {
            ElMessage.error("提交失败" + err)
        });
}

const reset = () => {
    tableData.value = []
    products.value = []
    billDate.value = new Date()
}

defineExpose({tableData});
</script>