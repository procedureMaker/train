import {createRouter, createWebHistory} from 'vue-router'
import {notification} from "ant-design-vue";
import store from "@/store";

const routes = [
    {
        path: '/',
        component: () => import('../views/main.vue'),
        meta: {
            loginRequire: true
        },
        children: [{
            path: 'welcome',
            component: () => import('../views/main/welcome.vue')
        }]
    },
    //默认重定向到welcome路由
    {
        path: '',
        redirect: '/welcome'
    },
    {
        path: '/login',
        component: () => import('../views/login.vue')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})
//防止用户通过导航栏输入url，没经过后端跳转到控制页面，路由做相应的拦截
// 路由登录拦截
router.beforeEach((to, from, next) => {
    // 要不要对meta.loginRequire属性做监控拦截
    if (to.matched.some(function (item) {
        console.log(item, "是否需要登录校验：", item.meta.loginRequire || false);
        return item.meta.loginRequire
    })) {
        const _member = store.state.member;
        console.log("页面登录校验开始：", _member);
        if (!_member.token) {
            console.log("用户未登录或登录超时！");
            notification.error({ description: "未登录或登录超时" });
            next('/login');
        } else {
            next();
        }
    } else {
        next();
    }
});
export default router
