import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from "path";

export default defineConfig({
  plugins: [vue()],
  server: {

    port: 9090, // 你需要定义的端口号

    proxy: {
      "/api": {
        target: "http://localhost:8080/",
        changeOrigin: true,
      },

    },
  },
  resolve: {
    // 配置路径别名
    alias: {
      '@': path.resolve(__dirname, './src'),
    }
  },

})
