<template>
    <div id="login">
        <el-row justify="center" align="middle" style="height: 100%">
            <el-col :span="8">
                <el-card style="background-color: rgba(255, 255, 255, 0.6);">
                    <template #header>
                        <div class="card-header">
                            <span>欢迎使用商城管理系统</span>
                        </div>
                    </template>
                    <el-form :model="form" label-width="auto" ref="loginFormRef" :rules="loginRules">
                        <div style="text-align: center; margin-bottom: 20px;">
                            用户注册
                        </div>
                        <el-form-item label="用户名" prop="username">
                            <el-input v-model="form.username"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱" prop="email">
                            <el-input v-model="form.email"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱验证" prop="emailCaptcha">
                            <el-row>
                                <el-col :span="8">
                                    <el-input v-model="form.emailCaptcha"></el-input>
                                </el-col>
                                <el-col :span="1"></el-col>
                                <el-col :span="10">
                                    <el-button type="primary" @click="getEmailCaptcha(loginFormRef)">发送验证码</el-button>
                                </el-col>
                            </el-row>
                        </el-form-item>
                        <el-form-item label="新密码" prop="password">
                            <el-input v-model="form.password" type="password"></el-input>
                        </el-form-item>
                        <el-form-item label="确认密码" prop="password2">
                            <el-input v-model="form.password2" type="password"></el-input>
                        </el-form-item>
                        <div style="text-align: center; padding-top: 20px;">
                            <el-button type="primary" @click="submitForm(loginFormRef)">注册</el-button>
                            <el-button @click="resetForm(loginFormRef)">重置</el-button>
                        </div>
                        <div style="text-align: center; padding-top: 20px;">
                            <el-link type="warning" href="/login">已有账号，去登录</el-link>
                        </div>
                    </el-form>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import {ref, reactive, onMounted} from "vue";
import { ElMessage } from "element-plus";
import type {FormInstance, FormRules } from "element-plus";
import http from "@/http/index";
import {useRoute, useRouter} from "vue-router";
import { Md5 } from 'ts-md5';

const route = useRoute();
const router = useRouter();

const loginFormRef = ref<FormInstance>();
const form = reactive({
    username: 'wwk123',
    email: 'wwk.jk@outlook.com',
    emailCaptcha: '202222',
    password: '123456',
    password2: '123456',
})

const validatePass2 = (rule: any, value: any, callback: any) => {
    if (value === "") {
        callback(new Error("请再次输入密码"));
    } else if (value !== form.password) {
        callback(new Error("两次密码不匹配!"));
    } else {
        callback();
    }
};

const loginRules = reactive<FormRules>({
    username: [
        {required: true, message: "请输入用户名", trigger: "blur"},
        {min: 6, message: "至少6位", trigger: "blur"},
    ],
    email: [
        {required: true, message: "请输入邮箱", trigger: "blur"},
        {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
        },
    ],
    emailCaptcha: [
        {required: true, message: "请输入邮箱验证码", trigger: "blur"},
        {min: 6, max: 6, message: "请输入6位数字", trigger: "blur"},
        {pattern: /^[0-9]*$/, message: "请输入6位数字", trigger: "blur"},
    ],
    password: [
        {required: true, message: "请输入密码", trigger: "blur"},
        {min: 6, message: "至少6位", trigger: "blur"},
    ],
    password2: [
        {required: true, message: "请输入密码", trigger: "blur"},
        {validator: validatePass2, trigger: "blur"}
    ],
});

const callApi = () => {
    http.post("/api/register", {
        username: form.username,
        email: form.email,
        emailCaptcha: form.emailCaptcha,

        password: Md5.hashStr(form.password).toUpperCase(),
        password2: Md5.hashStr(form.password2).toUpperCase(),
    })
        .then((res: any) => {
            if (res === 'ok') {
                ElMessage.success("注册成功")
                setTimeout(() => {
                    router.push({name: "login"}), 1000
                })
            }
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
    form.email = "";
    form.emailCaptcha = "";
    form.password = "";
    form.password2 = "";
};


const getEmailCaptcha = () => {
    // 邮箱的校验
    const emailReg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    if (emailReg.exec(form.email)) {
        http.post("/api/sendemailcaptcha", {
            email: form.email,
        })
            .then((res: any) => {
                console.log(res)
            })
            .catch((err: any) => {
            });
    }
}
// const getEmailCaptcha = async (formEl: FormInstance | undefined) => {
//     if (!formEl) return;
//     await formEl.validate((valid, fields) => {
//         if (valid) {
//             //验证成功，执行下面方法
//             emailApi();
//         } else {
//             // console.log('error submit!', fields)
//         }
//     });
// };
</script>

<style scoped>

    #login {
        height: calc(100vh - 2px);
        background: url("../../public/assets/login_bg.jpg");
        background-size: cover;
        /*background-repeat: no-repeat;*/
        /*background-position: center center; !* 背景图垂直、水平均居中 *!*/
        /*background-attachment: fixed;*/
    }

</style>