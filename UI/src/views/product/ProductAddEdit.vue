<template>
    <div>
        <el-card>
            <el-steps
                    :active="active"
                    finish-status="success"
                    style="margin: 20px;"
                    align-center
            >
                <el-step title="基本信息"></el-step>
                <el-step title="商品图片"></el-step>
                <el-step title="商品内容"></el-step>
            </el-steps>
            <el-form label-width="auto">
                <el-tabs
                        tab-position="left"
                        @tab-change="change"
                        v-model="tabIndex"
                >
                    <el-tab-pane label="基本信息" name="0">
                        <div class="h510">
                            <div>
                                <el-row>
                                    <el-col :span="2"></el-col>
                                    <el-col :span="17">
                                        <el-form-item label="商品名称">
                                            <el-input v-model="form.name"></el-input>
                                        </el-form-item>
                                        <el-form-item label="类别">
                                            <el-tree-select
                                                    v-model="form.categoryId"
                                                    :data="categoryData"
                                                    style="width: 100%;"
                                                    check-strictly=true
                                            ></el-tree-select>
                                        </el-form-item>
                                        <el-form-item label="价格">
                                            <el-input v-model="form.price"></el-input>
                                        </el-form-item>
                                        <el-form-item label="优先级">
                                            <el-input v-model="form.seq"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="商品图片">
                        <div class="h510" style="margin: 0 30px;">
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
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="商品内容">
                        <div class="h510">
                            <my-editor ref="editor"></my-editor>
                        </div>
                    </el-tab-pane>
                </el-tabs>
                <div style="text-align: center; margin-top: 20px;">
                    <el-button type="primary"
                               @click="submit"
                    >
                        提交
                    </el-button>
                    <el-button type="success" @click="tabsNext">Next</el-button>
                </div>
            </el-form>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import MyEditor from '@/components/MyEditor.vue'
import {onMounted, ref} from 'vue'
import http from "../../http";
import {ElMessage} from "element-plus/es";
import {useRoute, useRouter} from "vue-router";

const route = useRoute();
const router = useRouter();

const active = ref(0);
const tabIndex = ref("0");

// 获取子组件富文本编辑器中的数据
const editor = ref();

const form = ref({
    id: '',
    name: '',
    categoryId: '0',
    price: '',
    seq: '',
    brief: '',
    img: '',
    imgName: '',
})

const categoryData = ref([
    {value: "0", label: "根节点"},
]);

onMounted(() => {
    initCategory()
    form.value.id = <string>route.query.id;
    if (form.value.id != null || form.value.id != undefined) {
        queryInfoById()
    }
})

const initCategory = () => {
    http
        .post("/api/category/page", {})
        .then((res: any) => {
            categoryData.value = categoryData.value.concat(res)
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
}

const queryInfoById = () => {
    http
        .post("/api/product/page", {
            id: form.value.id,
        })
        .then((res: any) => {
            form.value.id = res[0].id
            form.value.name = res[0].name
            form.value.categoryId = res[0].categoryId
            form.value.price = res[0].price
            form.value.seq = res[0].seq
            form.value.brief = res[0].brief
            form.value.img = res[0].img

            editor.value.valueHtml = res[0].brief

            console.log(editor.value.valueHtml);
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
}

// 当子页面发生改变时
const change = () => {
    if (isNaN(Number(tabIndex.value))) {
        active.value = 0
    } else {
        active.value = Number(tabIndex.value)
    }
}

// 点击下一个时
const tabsNext = () => {
    let temp = Number(active.value) + Number(1)
    if (temp > 2) {
        temp = 0
        // return
    }
    // active.value = temp
    tabIndex.value = String(temp)
}

const onchange = (file: any, fileList: any) => {
    var reader = new FileReader();
    reader.readAsDataURL(file.raw);
    reader.onload = () => {
        form.value.img = reader.result;
        form.value.imgName = file.raw.name;
    };
};

const submit = () => {
    form.value.brief = editor.value.valueHtml
    if (form.value.id == null) {
        http
            .post("/api/product/add", {
                name: form.value.name,
                categoryId: form.value.categoryId,
                price: form.value.price,
                seq: form.value.seq,
                brief: form.value.brief,
                img: form.value.img,
                imgName: form.value.imgName,
            })
            .then((res: any) => {
                if (res == 'ok') {
                    ElMessage.success("商品添加成功")
                    setTimeout(() => {
                        router.push({name: "productlist"}), 1000
                    })
                }
            })
            .catch((err: any) => {
                ElMessage.error("商品添加失败")
            });
    } else {
        console.log(form.value);
        http
            .post("/api/product/update", {
                id: form.value.id,
                name: form.value.name,
                categoryId: form.value.categoryId,
                price: form.value.price,
                seq: form.value.seq,
                brief: form.value.brief,
                img: form.value.img,
                imgName: form.value.imgName,
            })
            .then((res: any) => {
                if (res == 'ok') {
                    ElMessage.success("商品修改成功")
                    setTimeout(() => {
                        router.push({name: "productlist"}), 1000
                    })
                }
            })
            .catch((err: any) => {
                ElMessage.error("商品添加失败")
            });
    }

    // const reset = () => {
    //     form.value.id = ''
    //     form.value.name = ''
    //     form.value.categoryId = ''
    //     form.value.price = ''
    //     form.value.seq = ''
    //     form.value.brief = ''
    //     form.value.img = ''
    //     form.value.imgName = ''
    // }

}
</script>

<style scoped>
    .h510 {
        height: 510px;
    }

    .avatar-uploader .avatar {
        width: 200px;
        height: 200px;
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
        width: 200px;
        height: 200px;
        text-align: center;
    }
</style>