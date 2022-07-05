<template>
    <div @click="collapseChange"
         style="height: 30px;background-color: #545c64;text-align: center;line-height: 35px;
         font-size: 20px;cursor:pointer;">
        <el-icon v-if="isCollapse">
            <Expand/>
        </el-icon>
        <el-icon v-else-if="!isCollapse">
            <Fold/>
        </el-icon>
    </div>
    <el-menu
            active-text-color="#ffd04b"
            background-color="#545c64"
            style="border-right: none;"
            class="el-menu-vertical-demo"
            :default-openeds="opends"
            text-color="#fff"
            router
            :collapse="isCollapse"
            :collapse-transition="false"
    >
        <template v-for="(item0, index0) in $router.options.routes" :key="index0">
            <!--二级标签 首先根据show判断是否需要展示，然后再根据children判断是否存在子菜单，若存在子菜单则为二级菜单-->
            <el-sub-menu v-if="item0.meta.show == true && item0.children != null" :index="item0.path">
                <template #title>
                    <component :is="item0.meta.icon" class="icon"></component>
                    <span>{{ item0.meta.title }}</span>
                </template>
                <!-- 遍历二级菜单，遍历过程中根据show判断是否需要展示 -->
                <template v-for="item1 in item0.children">
                    <el-menu-item v-if="item1.meta.show == true" :index="item1.path">
                        <component :is="item1.meta.icon" class="icon"></component>
                        <span>{{ item1.meta.title }}</span>
                    </el-menu-item>
                </template>
            </el-sub-menu>
            <!--一级标签 首先根据show判断是否需要展示，然后再根据children判断是否存在子菜单，若不存在子菜单则为一级菜单-->
            <el-menu-item v-if="item0.meta.show == true && item0.children == null" :index="item0.path">
                <component :is="item0.meta.icon" class="icon"></component>
                <span>{{ item0.meta.title }}</span>
            </el-menu-item>
        </template>
    </el-menu>
</template>

<script lang="ts" setup>
import {ref, onMounted, onBeforeMount} from "vue";
import {useRoute, useRouter} from "vue-router";

import appStore from "@/store/appStore";
import {storeToRefs} from "pinia";

let {menuCollapse} = storeToRefs(appStore());

const route = useRoute();
const router = useRouter();
const isCollapse = ref(false);

const opends = ref([])

const collapseChange = () => {
    isCollapse.value = !isCollapse.value
    menuCollapse.value = isCollapse.value
}

onMounted(() => {
    isCollapse.value = menuCollapse.value
})

// 设置所有二级菜单自动展开
onBeforeMount(() => {
    router.options.routes.forEach(res => {
        if (res.children != null) {
            opends.value.push(res.path)
        }
    })
    console.log(opends.value);
})


</script>

<style scoped>
    .icon {
        width: 16px;
        height: 16px;
        padding-right: 5px;
    }
</style>