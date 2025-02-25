import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'  
import ElementPlus from 'element-plus'
//import 'element-plus/dist/index.css'

// router.beforeEach((to, from, next) => {
//     const waifuElement = document.getElementById('waifu');
//     if (waifuElement) {
//         if (to.path === '/main') {
//             waifuElement.style.display = 'none';
//         }
//     }
//     next();
    
//     if (from.path === '/main' && to.path !== '/main') {
//         // 如果必须强制页面跳转，拼接 base 并终止守卫
//         const fullPath = '/GlobalWindow' + to.fullPath;
//         window.location.assign(fullPath);
//         return; // 确保不再执行 next()
//     } else {
//         next(); // 正常放行
//     }
// });




const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.mount('#app')


