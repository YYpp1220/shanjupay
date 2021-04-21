import { RouteConfig } from 'vue-router'
import Layout from '@/layout/index.vue'

const billRoutes: RouteConfig = {
  path: '/bill',
  component: Layout,
  redirect: '/bill/index',
  meta: {
    code: 'sj_o_account',
    title: '账户管理',
    icon: 'form'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/bill/index.vue'),
      meta: {
        code: 'sj_o_account_check',
        title: '往来对账',
        icon: 'table'
      }
    },
    {
      path: 'unuse',
      component: () => import('@/views/bill/unuse.vue')
    }
  ]
}

export default billRoutes
