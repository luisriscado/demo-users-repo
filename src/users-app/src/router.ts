import Vue from 'vue';
import Router, { Route, RawLocation } from 'vue-router';
import session from '@/components/session';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      redirect: (to: Route): RawLocation => {
        if (session.isLogedIn()) {
          return '/users';
        }

        return '/login';
      },
    },
    {
      path: '/login',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Login.vue'),
      beforeEnter: (to: Route,
                    from: Route,
                    next: (to?: RawLocation | false | void) => void) => {
        if (session.isLogedIn()) {
          next('/users');
        }
        next();
      },
    },
    {
      path: '/users',
      name: 'users',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about"             */ './views/Users.vue'),
      beforeEnter: (to: Route,
                    from: Route,
                    next: (to?: RawLocation | false | void) => void) => {
        if (!session.isLogedIn()) {
          next('/login');
        }
        next();
      },
    },
  ],


});
