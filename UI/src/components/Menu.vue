<template>
    <el-menu
            active-text-color="#ffd04b"
            background-color="#545c64"
            class="el-menu-vertical-demo"
            default-active="/"
            text-color="#fff"
            @open="handleOpen"
            @close="handleClose"
            style="border-right: none;"
            router
    >
        <div v-for="(item0, index0) in $router.options.routes" :key="index0">
            <!-- 二级标签   首先根据show判断是否需要展示，然后再根据children判断是否存在子菜单，若存在子菜单则为二级菜单 -->
            <el-sub-menu v-if="item0.meta.show == true && item0.children != null" :index="item0.path">
                <template #title>
                    <component :is="item0.meta.icon" style="width: 16px; height: 16px; padding-right: 5px;"></component>
                    <span>{{ item0.meta.title }}</span>
                </template>
                <!-- 遍历二级菜单，遍历过程中根据show判断是否需要展示 -->
                <template v-for="item1 in item0.children">
                    <el-menu-item v-if="item1.meta.show == true" :index="item1.path">
                        <component :is="item1.meta.icon" style="width: 16px; height: 16px; padding-right: 5px;"></component>
                        <span>{{ item1.meta.title }}</span>
                    </el-menu-item>
                </template>
            </el-sub-menu>
            <!-- 一级标签   首先根据show判断是否需要展示，然后再根据children判断是否存在子菜单，若不存在子菜单则为一级菜单 -->
            <el-menu-item v-if="item0.meta.show == true && item0.children == null" :index="item0.path">
                <component :is="item0.meta.icon" style="width: 16px; height: 16px; padding-right: 5px;"></component>
                <span>{{ item0.meta.title }}</span>
            </el-menu-item>
        </div>
    </el-menu>
</template>

<script lang="ts" setup>

</script>

<style scoped>

</style>