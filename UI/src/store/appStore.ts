import { defineStore } from 'pinia'

const appStore = defineStore({
  id: 'app',
  state: () => {
    return {
      user: {
        id: 0,
        username: "",
        nickname: "",
      },
      token: "",
    }
  },
  getters: {

  },
  actions: {

  },
  // 开启数据缓存
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'com.mall',
        storage: localStorage,
      }
    ]
  }
})

export default appStore;