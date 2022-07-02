<template>
    <div>
        <el-card>
            <el-steps
                    :active="active"
                    finish-status="success"
                    style="margin: 20px 100px;"
            >
                <el-step title="基本信息"></el-step>
                <el-step title="商品参数"></el-step>
                <el-step title="商品属性"></el-step>
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
                                    <el-col :span="1"></el-col>
                                    <el-col :span="10">
                                        <el-form-item label="商品名称">
                                            <el-input v-model="form.name"></el-input>
                                        </el-form-item>
                                        <el-form-item label="单位">
                                            <el-input v-model="form.unit"></el-input>
                                        </el-form-item>
                                        <el-form-item label="类别">
                                            <el-tree-select
                                                    v-model="form.categoryId"
                                                    :data="categoryData"
                                                    style="width: 100%;"
                                                    check-strictly=true
                                            ></el-tree-select>
                                        </el-form-item>
                                        <el-form-item label="产地">
                                            <el-input v-model="form.addr"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="1"></el-col>
                                    <el-col :span="10">
                                        <el-form-item label="副标题">
                                            <el-input v-model="form.caption"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="商品参数">
                        <div class="h510">
                            2
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="商品属性">
                        <div class="h510">
                            3
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="商品图片">
                        <div class="h510">
                            4
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

const form = ref({
    name: '',
    caption: '',
    categoryId: '0',
    unit: '',
    addr: '',
    info: '',
})

const categoryData = ref([
    {value: "0", label: "根节点"},
]);

onMounted(() => {
    http
        .post("/api/category/page", {})
        .then((res: any) => {
            categoryData.value = categoryData.value.concat(res)
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
})

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
    if (temp > 4) {
        temp = 0
        // return
    }
    active.value = temp
    tabIndex.value = String(temp)
}

// 获取子组件富文本编辑器中的数据
const editor = ref(null);
const submit = () => {
    form.value.info = editor.value.valueHtml
    http
        .post("/api/goods/add", {
            name: form.value.name,
            caption: form.value.caption,
            categoryId: form.value.categoryId,
            unit: form.value.unit,
            addr: form.value.addr,
            info: form.value.info,
        })
        .then((res: any) => {
            if (res == 'ok') {
                ElMessage.success("商品添加成功")
                setTimeout(() => {
                    router.push({name: "index"}), 1000
                })
            }
        })
        .catch((err: any) => {
            ElMessage.error("数据初始化失败")
        });
}
</script>

<style>
    .h510 {
        height: 510px;
    }
</style>