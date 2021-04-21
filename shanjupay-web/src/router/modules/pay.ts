import { RouteConfig } from 'vue-router'
import Layout from '@/layout/index.vue'

const payRoutes: RouteConfig = {
  path: '/pay',
  component: Layout,
  redirect: '/pay/index',
  meta: {
    code: 'sj_m_app',
    title: '支付应用管理',
    icon: 'form'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/pay/index.vue'),
      meta: {
        code: 'sj_m_app_list',
        title: '应用管理',
        icon: 'table'
      }
    },
    {
      path: 'set/:id/:step',
      component: () => import('@/views/pay/set.vue'),
      meta: {
        title: '配置参数',
        hidden:true
      }
    },
    {
      path: 'start',
      component: () => import('@/views/pay/start.vue'),
      meta: {
        code: 'sj_m_payment',
        title: '开始支付',
        icon: 'table'
      }
    },
    {
      path: 'bill',
      component: () => import('@/views/pay/bill.vue'),
      meta: {
        code: 'sj_m_account_check',
        title: '应用财务对账',
        icon: 'table'
      }
    },
    // {
    //   path: 'deal',
    //   component: () => import('@/views/pay/deal.vue'),
    //   meta: {
    //     code: 'sj_m_transaction_list',
    //     title: '应用交易总览',
    //     icon: 'table'
    //   }
    // }
  ]
}

export default payRoutes
