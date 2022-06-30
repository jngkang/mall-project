<template>
    <div>
        <el-breadcrumb separator="/">
            <!-- 当点击返回时，返回上一级 -->
            <el-breadcrumb-item @click="goback">
                <a>
                    <el-icon style="vertical-align: -10%;">
                        <Back/>
                    </el-icon>
                    返回
                </a>
            </el-breadcrumb-item>
            <!-- 获取当前的路由，并依次浸进行遍历 -->
            <el-breadcrumb-item v-for="(item, index) in navArray">
                {{ item.meta.title }}
            </el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script lang="ts" setup>
import {useRoute, useRouter} from "vue-router";
import {watch, ref} from "vue";

const router = useRouter();
const route = useRoute();
const navArray = ref<any>([]);

// 监听，当路由发生改变时，进行相应的操作
watch(() => route.path, (newValue, oldValue) => {
        navArray.value = route.matched;
    }
);

// 返回上一级
const goback = () => {
    router.back();
};
</script>