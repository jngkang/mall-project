<template>
    <div style="border: 1px solid #ccc">
        <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :defaultConfig="toolbarConfig"
                :mode="mode"
        />
        <Editor
                style="height: 430px; overflow-y: hidden;"
                v-model="valueHtml"
                :defaultConfig="editorConfig"
                :mode="mode"
                @onCreated="handleCreated"
                @onChange="handleChange"
        />
    </div>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import {onBeforeUnmount, ref, shallowRef, onMounted} from 'vue'
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'
import {ElMessage} from 'element-plus'
import {storeToRefs} from 'pinia/dist/pinia'
import appStore from '../store/appStore'

let { token } = storeToRefs(appStore())

export default {
    components: { Editor, Toolbar },
    setup () {
        // 编辑器实例，必须用 shallowRef
        const editorRef = shallowRef()

        // 内容 HTML
        const valueHtml = ref('')

        const toolbarConfig = {}
        const editorConfig = {
            placeholder: '请输入内容...',
            MENU_CONF: {}
        }

        editorConfig.MENU_CONF['uploadImage'] = {
            // 上传图片的配置
            server: '/api/upload',
            // 单个文件的最大体积限制，默认为 2M
            maxFileSize: 10 * 1024 * 1024,
            // 最多可上传几个文件，默认为 100
            maxNumberOfFiles: 10,

            // form-data fieldName ，默认值 'wangeditor-uploaded-image'
            fieldName: 'file',

            // 单个文件上传成功之后
            onSuccess (file, res) {
                ElMessage.success(`${file.name} 上传成功`, res)
                console.log(`${file.name} 上传成功`, res)
            },
            // 单个文件上传失败
            onFailed (file, res) {
                ElMessage.error(`${file.name} 上传失败`, res)
                console.log(`${file.name} 上传失败`, res)
            },

            // 自定义增加 http  header
            headers: {
                Authorization: token.value,
            },

            // 小于该值就插入 base64 格式（而不上传），默认为 0
            base64LimitSize: 5 * 1024 // 50kb
        }

        // 组件销毁时，也及时销毁编辑器
        onBeforeUnmount(() => {
            const editor = editorRef.value
            if (editor == null) return
            editor.destroy()
        })

        const handleCreated = (editor) => {
            editorRef.value = editor // 记录 editor 实例，重要！
        }

        const handleChange = () => {
            // console.log(valueHtml.value)
        }

        return {
            editorRef,
            valueHtml,
            mode: 'default', // 或 'simple'
            toolbarConfig,
            editorConfig,
            handleCreated,
            handleChange,
        }
    }
}
</script>