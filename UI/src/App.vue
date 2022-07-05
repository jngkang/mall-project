<template>
    <div class="common-layout" v-if="$route.meta.isFrame">
        <el-container>
            <!-- 头 -->
            <el-header style="background-color: slategray;">
                <el-row :gutter="20" align="middle">
                    <el-col :span="4">
                        <img src="../assets/logo.png" alt="" width="55" height="55">
                    </el-col>
                    <el-col :span="16"></el-col>
                    <el-col :span="4" style="text-align: right;">
                        <el-dropdown class="dropdown">
                            <span class="el-dropdown-link">
                              {{ nickname }}
                              <el-icon class="el-icon--right"><arrow-down/></el-icon>
                            </span>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item>Action 1</el-dropdown-item>
                                    <el-dropdown-item>Action 2</el-dropdown-item>
                                    <el-dropdown-item>Action 3</el-dropdown-item>
                                    <el-dropdown-item divided @click="logout">退出</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </el-col>
                </el-row>
            </el-header>
            <el-container style="height: calc(100vh - 60px);">
                <!-- 左 -->
                <el-aside :width="isCollapse ?'64px':'200px'" style="background-color: #545c64;">
                    <menu-vue></menu-vue>
                </el-aside>
                <!-- 右 -->
                <el-main>
                    <div style="height: 30px; line-height: 30px;">
                        <nav-head-vue></nav-head-vue>
                    </div>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
    <router-view v-if="!$route.meta.isFrame"></router-view>
</template>

<script lang="ts" setup>
import MenuVue from '@/components/Menu.vue';
import NavHeadVue from "@/components/NavHead.vue";
import {ref, onMounted, watch} from "vue"
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus/es";
import appStore from "@/store/appStore";
import {storeToRefs} from "pinia";

let {menuCollapse} = storeToRefs(appStore());

const route = useRoute();
const router = useRouter();

let {user} = storeToRefs(appStore());

const nickname = ref('游客')
const isCollapse = ref(false)

onMounted(() => {
    if (user.value.nickname != '') {
        nickname.value = user.value.nickname;
    }
    isCollapse.value = menuCollapse.value
});

watch(
    () => menuCollapse.value,
    (newValue, oldValue) => {
        isCollapse.value = menuCollapse.value
    }
);

watch(
    () => user.value.nickname,
    (newValue, oldValue) => {
        nickname.value = newValue;
    }
);

const logout = () => {
    window.localStorage.clear();
    ElMessage.success("退出成功")
    setTimeout(() => {
        router.push({name: "login"}), 1000
    })
}
</script>
<style scoped>
    .dropdown {
        color: white;
        font-size: 16px;
        margin: 0 20px;
    }

    .example-showcase .el-dropdown-link {
        cursor: pointer;
        color: var(--el-color-primary);
        display: flex;
        align-items: center;
    }

    .el-aside {
        transition: width 0.15s;
        -webkit-transition: width 0.15s;
        -moz-transition: width 0.15s;
        -webkit-transition: width 0.15s;
        -o-transition: width 0.15s;
    }
</style>