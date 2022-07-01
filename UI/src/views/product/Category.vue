<template>
    <div>
        <el-button type="primary" @click="addClick">Primary</el-button>
    </div>
    <div>
        <el-drawer v-model="drawer"
                   direction="rtl"
                   size="35%"
        >
            <template #title>
                <h4>商品品类添加</h4>
            </template>
            <template #default>
                <div>
                    <el-form label-width="auto">
                        <el-form-item label="类别名称">
                            <el-input v-model="form.name"></el-input>
                        </el-form-item>
                        <el-form-item label="图片">
                            <el-upload
                                    class="avatar-uploader"
                                    ref="uploadRef"
                                    :auto-upload="false"
                                    :show-file-list="false"
                                    :on-change="onchange"
                            >
                                <img v-if="form.img" :src="form.img" class="avatar"/>
                                <el-icon v-else class="avatar-uploader-icon">
                                    <Plus/>
                                </el-icon>
                            </el-upload>
                        </el-form-item>
                        <el-form-item label="所属父类">
                            <el-tree-select v-model="form.pid" :data="categoryData" style="width: 100%;"></el-tree-select>
                        </el-form-item>
                        <el-form-item label="优先级">
                            <el-input v-model="form.priority"></el-input>
                        </el-form-item>
                    </el-form>
                </div>
            </template>
            <template #footer>
                <div style="flex: auto; text-align: center;">
                    <el-button @click="cancelClick">取消</el-button>
                    <el-button type="primary" @click="confirmClick">提交</el-button>
                </div>
            </template>
        </el-drawer>
    </div>
</template>

<script lang="ts" setup>
import {ref, reactive, onMounted} from "vue";
import {ElMessageBox} from 'element-plus'
import http from "@/http/index";
import type {UploadInstance} from "element-plus";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus/es";

const uploadRef = ref<UploadInstance>();
const route = useRoute();
const router = useRouter();
const form = reactive({
    name: "",
    priority: 0,
    img: "",
    pid: "0",
    imgName: "",
});
const categoryData = ref([
    {value: "0", label: "根节点"},
]);

onMounted(() => {

})

const addClick = () => {
    http
        .post("/api/category/page", {
            pid: 0,
        })
        .then((res: any) => {
            for (let resKey in res) {
                console.log(res[resKey]);
                categoryData.value.push({value: res[resKey].id, label: res[resKey].name})
            }
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
    drawer.value = true
}

const drawer = ref(false)

function cancelClick() {
    drawer.value = false
    reset()
}

const onchange = (file: any, fileList: any) => {
    var reader = new FileReader();
    reader.readAsDataURL(file.raw);
    reader.onload = () => {
        form.img = reader.result;
        form.imgName = file.raw.name;
    };
};

const confirmClick = () => {
    http
        .post("/api/category/add", {
            name: form.name,
            img: form.img,
            priority: form.priority,
            pid: form.pid,
            imgName: form.imgName,
        })
        .then((res: any) => {
            ElMessage.success("添加成功")
            cancelClick()

        })
        .catch((err: any) => {
            console.log(err);
            ElMessage.error("添加失败")
        });
}

const reset = () => {
    form.name = '',
        form.img = '',
        form.priority = 0,
        form.pid = '0'
}
</script>

<style scoped>
    .avatar-uploader .avatar {
        width: 80px;
        height: 80px;
        display: block;
    }
</style>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed var(--el-border-color);
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        transition: var(--el-transition-duration-fast);
    }

    .avatar-uploader .el-upload:hover {
        border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 80px;
        height: 80px;
        text-align: center;
    }
</style>