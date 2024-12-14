const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "index", component: () => import("pages/IndexPage.vue") },
      { path: "about", component: () => import("pages/AboutPage.vue") },
      { path: "test", component: () => import("pages/TestPage.vue") },
      { path: "steven1", component: () => import("pages/StevenTestPage1.vue") },
      { path: "steven2", component: () => import("pages/StevenTestPage2.vue") },
      { path: "steven3", component: () => import("pages/StevenTestPage3.vue") },
      { path: "steven4", component: () => import("pages/StevenTestPage4.vue") },
    ],
  },
  {
    path: "/123",
    component: () => import("pages/MyPage.vue"),
  },
  {
    path: "/123456",
    component: () => import("pages/MainForm.vue"),
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
