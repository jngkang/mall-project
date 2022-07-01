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
            <el-table-column label="图片" align="center" width="120">
                <template #default="scope">
                    <el-image
                            style="width: 80px; height: 80px"
                            :src="scope.row.img"
                            :preview-src-list="[scope.row.img]"
                            fit="cover"
                            preview-teleported="true"
                    ></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="statusX" label="状态" sortable align="center" width="80"></el-table-column>
            <el-table-column prop="updateBy" label="最后一次更新者" sortable align="center"></el-table-column>
            <el-table-column prop="updateTime" label="最后一次更新时间" sortable align="center"></el-table-column>
        </el-table>
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
                            <el-tree-select
                                    v-model="form.pid"
                                    :data="categoryData"
                                    style="width: 100%;"
                                    check-strictly=true
                            ></el-tree-select>
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

const tableData = ref([])

onMounted(() => {
    http
        .post("/api/category/page", {})
        .then((res: any) => {
            console.log(res);
            tableData.value = res
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
})

const addClick = () => {
    categoryData.value = [{value: "0", label: "根节点"}]
    http
        .post("/api/category/page", {
            deep: 2,
        })
        .then((res: any) => {
            categoryData.value = categoryData.value.concat(res)
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