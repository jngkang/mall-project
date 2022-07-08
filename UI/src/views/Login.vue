<template>
    <div id="login">
        <el-row justify="center" align="middle" style="height: 100%">
            <el-col :span="7">
                <el-card style="background-color: rgba(255, 255, 255, 0.6);">
                    <template #header>
                        <div class="card-header">
                            <span>欢迎使用商城管理系统</span>
                        </div>
                    </template>
                    <el-form :model="form" label-width="auto" ref="loginFormRef" :rules="loginRules">
                        <div style="text-align: center; margin-bottom: 20px;">
                            登录
                        </div>
                        <el-form-item label="用户名" prop="username">
                            <el-input v-model="form.username"></el-input>
                        </el-form-item>
                        <el-form-item label="密码" prop="password">
                            <el-input v-model="form.password" type="password"></el-input>
                        </el-form-item>
                        <div style="text-align: center; padding-top: 30px;">
                            <el-button type="primary" @click="submitForm(loginFormRef)">登录</el-button>
                            <el-button @click="resetForm(loginFormRef)">重置</el-button>
                        </div>
                        <div style="text-align: center; padding-top: 20px;">
                            <el-link type="warning" href="/#/register">还没有账号，去注册</el-link>
                        </div>
                    </el-form>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import {ref, reactive, onMounted} from "vue";
import http from "@/http/index";
import {ElMessage} from "element-plus";
import type {FormInstance, FormRules} from "element-plus";
import {storeToRefs} from "pinia";
import jwtDecode from "jwt-decode";
import {useRoute, useRouter} from "vue-router";
import appStore from "@/store/appStore";
import {Md5} from 'ts-md5';

const route = useRoute();
const router = useRouter();

let {user, token} = storeToRefs(appStore());


const loginFormRef = ref<FormInstance>();
const form = reactive({
    username: 'admin',
    password: '123456',
})

const loginRules = reactive<FormRules>({
    username: [
        {required: true, message: "请输入用户名", trigger: "blur"},
    ],
    password: [
        {required: true, message: "请输入密码", trigger: "blur"},
        {min: 6, message: "不少于6位", trigger: "blur"},
    ],
});

const callApi = () => {
    http.post("/api/login", {
        username: form.username,
        password: Md5.hashStr(form.password).toUpperCase(),
    })
        .then((res: any) => {
            const tokenObj: any = jwtDecode(res);
            user.value.id = tokenObj.id;
            user.value.username = tokenObj.username;
            user.value.nickname = tokenObj.nickname;
            token.value = res;
            ElMessage.success("登录成功")
            setTimeout(() => {
                router.push({name: "index"}), 1000
            })
        })
        .catch((err: any) => {
        });
}

const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    await formEl.validate((valid, fields) => {
        if (valid) {
            //验证成功，执行下面方法
            callApi();
        } else {
            // console.log('error submit!', fields)
        }
    });
};
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    formEl.resetFields();
    form.username = "";
    form.password = "";
};
</script>

<style scoped>

    #login {
        height: calc(100vh - 2px);;
        background: url("../../public/assets/login_bg.jpg");
        background-size: cover;
        /*background-repeat: no-repeat;*/
        /*background-position: center center; !* 背景图垂直、水平均居中 *!*/
        /*background-attachment: fixed;*/
    }

</style>