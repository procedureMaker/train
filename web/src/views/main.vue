<template>
  <a-layout id="components-layout-demo-top-side-2">
    <the-header></the-header>
    <a-layout>
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
            v-model:selectedKeys="selectedKeys2"
            v-model:openKeys="openKeys"
            mode="inline"
            :style="{ height: '100%', borderRight: 0 }"
        >
          <a-sub-menu key="sub1">
            <template #title>
              <span>
                <user-outlined/>
                subnav 1
              </span>
            </template>
            <a-menu-item key="1">option1</a-menu-item>
            <a-menu-item key="2">option2</a-menu-item>
            <a-menu-item key="3">option3</a-menu-item>
            <a-menu-item key="4">option4</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub2">
            <template #title>
              <span>
                <laptop-outlined/>
                subnav 2
              </span>
            </template>
            <a-menu-item key="5">option5</a-menu-item>
            <a-menu-item key="6">option6</a-menu-item>
            <a-menu-item key="7">option7</a-menu-item>
            <a-menu-item key="8">option8</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub3">
            <template #title>
              <span>
                <notification-outlined/>
                subnav 3
              </span>
            </template>
            <a-menu-item key="9">option9</a-menu-item>
            <a-menu-item key="10">option10</a-menu-item>
            <a-menu-item key="11">option11</a-menu-item>
            <a-menu-item key="12">option12</a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 0 24px 24px">
        <a-breadcrumb style="margin: 16px 0">
          <a-breadcrumb-item>Home</a-breadcrumb-item>
          <a-breadcrumb-item>List</a-breadcrumb-item>
          <a-breadcrumb-item>App</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
          所有会员总数：{{ count }}
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>

</template>
<script>
import {UserOutlined, LaptopOutlined, NotificationOutlined} from '@ant-design/icons-vue';
import {defineComponent, ref} from 'vue';
import TheHeader from "@/components/the-header";
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "main-view",
  components: {
    TheHeader,
    UserOutlined,
    LaptopOutlined,
    NotificationOutlined,
  },
  setup() {
    // const count：声明了一个常量 count，它是一个响应式引用对象。
    // ref(0)：调用 ref 函数，传入初始值 0，将 0 转换为一个响应式引用。
    // count.value：通过 .value 访问或修改响应式引用的值。
    const count = ref(0);
    axios.get("/member/member/count").then(response => {
      let data = response.data;     // 获取响应体中的数据
      if (data.success) {
        // data.content：如果请求成功，后端返回的实际数据存储在 content 属性中。
        // data.message：如果请求失败，后端返回的错误信息存储在 message 属性中
        count.value = data.content; // 如果成功，更新响应式数据
      } else {
        notification.error({description: data.message});
      }
    });


    return {
      count,
      selectedKeys2: ref(['1']),
      collapsed: ref(false),
      openKeys: ref(['sub1']),
    };
  },
});
</script>
<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>