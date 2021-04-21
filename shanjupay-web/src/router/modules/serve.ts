import { RouteConfig } from 'vue-router'
import Layout from '@/layout/index.vue'

const serveRoutes: RouteConfig = {
  path: '/serve',
  component: Layout,
  redirect: '/serve/index',
  meta: {
    code: 'sj_o_sys',
    title: '服务类型管理',
    icon: 'form'
  },
  children: [
    {
      path: 'serve',
      component: () => import('@/views/serve/serve.vue'),
      meta: {
        code: 'sj_o_service_type',
        title: '服务类型管理',
        icon: 'table'
      }
    },
    {
      path: 'index',
      component: () => import('@/views/serve/index.vue')
    }
  ]
}

export default serveRoutes
