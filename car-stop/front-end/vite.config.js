import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver, NaiveUiResolver } from 'unplugin-vue-components/resolvers'
import AutoImport from 'unplugin-auto-import/vite'
import legacy from '@vitejs/plugin-legacy';

export default defineConfig({
  base: './', //1.打包专用
  plugins: [
    vue(),
    legacy({ //2.打包专用(解决跨域)
      targets: ["ie>=11"],
      additionalLegacyPolyfills: ["regenerator-runtime/runtime"],
    }),
    AutoImport({
      imports: ['vue', 'vue-router', 'pinia']
    }),
    Components({
      resolvers: [ElementPlusResolver(), NaiveUiResolver()]
    })
  ],
  // server: {
  //   proxy: {
  //     '/api': {
  //       target: 'http://localhost:9000',
  //       changeOrigin: true,
  //       rewrite: (path) => path.replace(/^\/api/, '')
  //     }
  //   }
  // },
})


