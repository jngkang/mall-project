<template>
    <div>
        <el-drawer v-model="editDrawer"
                   direction="ltr"
                   size="45%"
                   :before-close="handleClose"
        >
            <template #title>
                <h4>供应商信息编辑</h4>
            </template>
            <template #default>
                <div>
                    <el-form label-width="auto">
                        <el-form-item label="类别名称">
                            <el-input v-model="form.name"></el-input>
                        </el-form-item>
                        <el-form-item label="城市">
                            <el-cascader
                                    size="large"
                                    :options="options"
                                    v-model="form.CAD"
                                    style="width: 100%;"
                                    placeholder="选择城市..."
                            >
                            </el-cascader>
                        </el-form-item>
                        <el-form-item label="详细地址">
                            <el-input v-model="form.address"></el-input>
                        </el-form-item>
                        <el-form-item label="优先级">
                            <el-input v-model="form.seq"></el-input>
                        </el-form-item>
                    </el-form>
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
import {defineExpose, getCurrentInstance, reactive, ref, onMounted} from "vue";
import {regionData} from 'element-china-area-data'
import http from "../../http";
import {ElMessage} from "element-plus/es";

const instance = getCurrentInstance();

const options = ref(regionData)
const editDrawer = ref(false)

const form = reactive({
    id: "",
    name: '',
    address: '',
    seq: 0,
    CAD: [],
});

const getById = (row) => {
    http
        .post("/api/vendor/select", {
            id: row.id,
        })
        .then((res: any) => {
            console.log(res);
            form.id = res[0].id
            form.name = res[0].name
            form.address = res[0].address
            form.seq = res[0].seq
            form.CAD = [res[0].province, res[0].city, res[0].district]
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
}

const handleClose = () => {
    form.id = ''
    form.name = ''
    form.address = ''
    form.seq = 0
    form.CAD = []
    editDrawer.value = false
}

const confirmClick = () => {
    http
        .post("/api/vendor/update", {
            id: form.id,
            name: form.name,
            province: form.CAD[0],
            city: form.CAD[1],
            district: form.CAD[2],
            address: form.address,
            seq: form.seq,
        })
        .then((res: any) => {
            if (res == 1) {
                ElMessage.success("数据修改成功")
                handleClose()
                instance.ctx.$parent.tableDataInit();
            }
        })
        .catch((err: any) => {
            ElMessage.error("数据修改失败")
        });
}

defineExpose({editDrawer, getById});
</script>

<style scoped>

</style>