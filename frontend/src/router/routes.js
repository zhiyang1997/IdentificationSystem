const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "index", component: () => import("pages/IndexPage.vue") },
      { path: "about", component: () => import("pages/AboutPage.vue") },
      { path: "test", component: () => import("pages/TestPage.vue") },
    ],
  },
  {
    path: "/123",
    component: () => import("pages/MyPage.vue"),
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
