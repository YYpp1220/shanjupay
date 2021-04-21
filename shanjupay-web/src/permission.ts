import router from './router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { Message } from 'element-ui'
import { Route } from 'vue-router'
import { UserModule } from '@/store/modules/user'
import { PermissionModule } from './store/modules/permission'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/operationLogin']

router.beforeEach(async (to: Route, _: Route, next: any) => {
  // Start progress bar
  NProgress.start()

  // Determine whether the user has logged in
  if (UserModule.token) {
    if (_.path === '/login') {
      // If is logged in, redirect to the home page
      next()

      NProgress.done()
    } else if (_.path === '/operationLogin') {
      next()
      if (to.path === '/') {
        next({ path: '/firm/index'})
        location.reload()
      }
      
      NProgress.done()
    } else {
      // Check whether the user has obtained his permission roles
      if (!UserModule.hasLoaded) {
        try {
          await UserModule.GetUserInfo()
          const menus = UserModule.menus
          if (menus !== undefined) {
            // Generate accessible routes map based on role
            PermissionModule.GenerateRoutes(menus)
            // Dynamically add accessible routes
            router.addRoutes(PermissionModule.dynamicRoutes)
          }

          // Hack: ensure addRoutes is complete
          // Set the replace: true, so the navigation will not leave a history record
          next({ ...to, replace: true })
        } catch (err) {
          // Remove token and redirect to login page
          UserModule.ResetToken()
          Message.error(err || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      } else {
        next()
      }
    }
  } else {
    // Has no token
    if (whiteList.indexOf(to.path) !== -1) {
      // In the free login whitelist, go directly
      next()
    } else {
      // Other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach((to: Route) => {
  // Finish progress bar
  NProgress.done()

  // set page title
  document.title = to.meta.title
})
