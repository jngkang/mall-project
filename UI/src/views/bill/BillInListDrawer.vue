<template>
    <div>
        <el-drawer v-model="productListDrawer"
                   direction="ltr"
                   size="60%"
                   :before-close="handleClose"
        >
            <template #title>
                <h4>商品信息</h4>
            </template>
            <template #default>
                <div>
                    <div style="text-align: center;">
                        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
                            <el-form-item label="商品名称">
                                <el-input v-model="queryForm.query" placeholder="商品名称"></el-input>
                            </el-form-item>
                            <el-form-item label="商品品类">
                                <el-tree-select
                                        v-model="queryForm.categoryId"
                                        :data="categoryData"
                                        style="width: 100%;"
                                        check-strictly=true
                                        placeholder="请选择商品品类..."
                                        clearable
                                        @clear="tableDataInit"
                                ></el-tree-select>
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
                            ref="elTableRef"
                            @selection-change="handleSelectionChange"
                            :row-key="getRowKey"
                    >
                        <el-table-column type="selection" width="55" align="center"></el-table-column>
                        <el-table-column prop="id" label="#ID" align="center" width="80" fixed="left"></el-table-column>
                        <el-table-column prop="name" label="商品名称" align="center" fixed="left"></el-table-column>
                        <el-table-column label="图片" align="center" width="100">
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
                    </el-table>
                </div>
            </template>
            <template #footer>
                <div style="flex: auto; text-align: center;">
                    <el-button @click="handleClose">取消</el-button>
                    <el-button type="primary" @click="confirmClick">提交</el-button>
                </div>
            </template>
        </el-drawer>
    </div>
</template>

<script lang="ts" setup>
import {ref, defineExpose, reactive, onMounted, onBeforeMount, getCurrentInstance} from "vue";
import http from "../../http";
import {ElMessage, ElTable} from "element-plus/es";
import appStore from "@/store/appStore";
import {storeToRefs} from "pinia";
import {TIMEOUT} from "dns";

let {products} = storeToRefs(appStore());

const productListDrawer = ref(false)
const tableData = ref([])
const tableSelectData = ref([])
const categoryData = ref([
    {value: "0", label: "根节点"},
]);
const queryForm = reactive({
    query: "",
    categoryId: "",
});

const vendorSelectData = ref([])

const instance = getCurrentInstance();
const elTableRef = ref(null)

onMounted(() => {
    initCategory()
})

const openInit = () => {
    tableDataInit()
}


const handleSelectionChange = (val) => {
    tableSelectData.value = val
}

const confirmClick = () => {
    instance.ctx.$parent.tableData = tableSelectData.value
    products.value = tableSelectData.value
    productListDrawer.value = false
}

const initCategory = () => {
    // 初始化表格数据
    http
        .post("/api/category/page", {})
        .then((res: any) => {
            // console.log(res);
            categoryData.value = [{value: "0", label: "根节点"}]
            // 下拉菜单
            categoryData.value = categoryData.value.concat(res)
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
}

const tableDataInit = () => {
    http
        .post("/api/product/page", {
            name: queryForm.query,
            categoryId: queryForm.categoryId,
        })
        .then((res: any) => {
            tableData.value = []
            res.forEach(r => {
                tableData.value.push({id: r.id, img: r.img, name: r.name, qty: 1})
            })

            // TODO 失效原因：数据在内存中的地址不同导致的，需要用双层循环

            if (vendorSelectData) {
                vendorSelectData.value.forEach(res => {
                    console.log(res);
                    tableData.value.forEach(r => {
                        if (res.id === r.id) {
                            console.log(r);
                            console.log(r.id);
                            console.log(res.id);
                            elTableRef.value!.toggleRowSelection(r)
                        }
                    })
                })
            } else {
                elTableRef.value!.clearSelection()
            }
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败" + err)
        });
}

const getRowKey = (row) => {
    return row.id + ''
}

const handleClose = () => {
    queryForm.query = ''
    queryForm.categoryId = ''
    productListDrawer.value = false
}

defineExpose({productListDrawer, openInit, vendorSelectData});
</script>