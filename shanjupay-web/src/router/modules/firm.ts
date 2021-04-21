import { RouteConfig } from 'vue-router'
import Layout from '@/layout/index.vue'

const firmRoutes: RouteConfig = {
  path: '/firm',
  component: Layout,
  redirect: '/firm/index',
  meta: {
    code: 'sj_o_entreprise',
    title: '企业管理',
    icon: 'form'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/firm/index.vue'),
      meta: {
        code: 'sj_o_entreprise_list',
        title: '企业管理',
        icon: 'table'
      }
    },
    {
      path: 'aduit',
      component: () => import('@/views/firm/aduit.vue'),
      meta: {
        code: 'sj_o_audit',
        title: '审核管理',
        icon: 'table'
      }
    },
    {
      path: 'member',
      component: () => import('@/views/firm/member.vue'),
      meta: {
        code: 'sj_o_member_list',
        title: '会员管理',
        icon: 'table'
      }
    }
  ]
}

export default firmRoutes
