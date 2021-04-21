import { RouteConfig } from 'vue-router'
import Layout from '@/layout/index.vue'

const financeRoutes: RouteConfig = {
  path: '/finance',
  component: Layout,
  redirect: '/finance/index',
  meta: {
    code: 'sj_m_account',
    title: '账户管理',
    icon: 'form'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/finance/index.vue'),
      meta: {
        code: 'sj_m_account_list',
        title: '账户中心',
        icon: 'table'
      }
    },
    {
      path: 'approve',
      component: () => import('@/views/finance/approve.vue'),
      meta: {
        code: 'sj_m_enterprise_auth',
        title: '资质申请',
        icon: 'table'
      }
    }
    // {
    //   path: 'list',
    //   component: () => import('@/views/house/list.vue'),
    //   meta: {
    //     title: '房源列表',
    //     icon: 'table'
    //   }
    // }
  ]
}

export default financeRoutes
