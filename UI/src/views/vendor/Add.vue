<template>
    <div>
        <el-drawer v-model="addDrawer"
                   direction="rtl"
                   size="45%"
                   :before-close="handleClose"
        >
            <template #title>
                <h4>供应商信息添加</h4>
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
import {defineExpose, reactive, ref, getCurrentInstance} from "vue";
import {regionData} from 'element-china-area-data'
import http from "../../http";
import {ElMessage} from "element-plus/es";

const instance = getCurrentInstance();
const options = ref(regionData)

const addDrawer = ref(false)
const form = reactive({
    name: '',
    address: '',
    seq: 0,
    CAD: [],
});

const handleClose = () => {
    form.name = ''
    form.address = ''
    form.seq = 0
    form.CAD = []
    addDrawer.value = false
}

const confirmClick = () => {
    http
        .post("/api/vendor/insert", {
            name: form.name,
            province: form.CAD[0],
            city: form.CAD[1],
            district: form.CAD[2],
            address: form.address,
            seq: form.seq,
        })
        .then((res: any) => {
            if (res == 1) {
                ElMessage.success("数据添加成功")
                handleClose()
                instance.ctx.$parent.tableDataInit();
            }
        })
        .catch((err: any) => {
            ElMessage.error("数据添加失败")
        });
}

defineExpose({addDrawer});
</script>

<style scoped>

</style>