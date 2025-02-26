<template>
  <!--  <a-button type="primary" @click="showModal">新增</a-button>-->
  <!--p防止与下面元素重叠 -->
  <p>
    <a-space>
      <a-button type="primary" @click="showModal">新增</a-button>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
    </a-space>
  </p>

  <!--乘车人员展示-->
  <a-table :dataSource="passengers"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading"/>
  <!-- 新增弹窗 -->
  <a-modal v-model:visible="visible" title="乘车人" @ok="handleOk" ok-text="确认" cancel-text="取消">
    <a-form
        :model="passenger"
        name="basic"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 16 }"
        autocomplete="off"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
    >
      <a-form-item label="姓名">
        <a-input v-model:value="passenger.name"/>
      </a-form-item>

      <a-form-item label="身份证">
        <a-input v-model:value="passenger.idCard"/>
      </a-form-item>

      <a-form-item label="类型">
        <a-select v-model:value="passenger.type">
          <a-select-option value="1">成人</a-select-option>
          <a-select-option value="2">儿童</a-select-option>
          <a-select-option value="3">学生</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import {defineComponent, ref, reactive, onMounted} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "passenger-view",
  setup() {

    const passenger = reactive({
      id: undefined,
      memberId: undefined,
      name: undefined,
      idCard: undefined,
      type: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const onFinish = values => {
      console.log('Success:', values);
    };
    const onFinishFailed = errorInfo => {
      console.log('Failed:', errorInfo);
    };

    const visible = ref(false);
    const showModal = () => {
      visible.value = true;
    };
    const handleOk = () => {
      axios.post("/member/passenger/save", passenger).then(response => {
        let data = response.data;
        if (data.success) {
          notification.success({description: "乘车人基本信息保存成功"})
          visible.value = false;
          handleQuery({
            page: pagination.current,
            size: pagination.pageSize
          });
        } else {
          notification.error({description: data.message})
        }
      })
    };
    const pagination = reactive({
      total: 0,
      current: 1,
      pageSize: 2
    });
    const passengers = ref([]);
    // const dataSource = [
    //   {
    //     key: '1',
    //     name: '胡彦斌',
    //     age: 32,
    //     address: '西湖区湖底公园1号',
    //   },
    //   {
    //     key: '2',
    //     name: '胡彦祖',
    //     age: 42,
    //     address: '西湖区湖底公园1号',
    //   },
    // ];
    let loading = ref(false);
    const columns = [
      {
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '身份证',
        dataIndex: 'idCard',
        key: 'idCard',
      },
      {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
      },
    ];
    // handlerQuery 是一个执行异步网络请求的副作用函数（发起 HTTP 请求并更新响应式数据 passengers.value）。
    // 它的核心目的是触发一个动作（发送请求并处理结果），而不是计算或返回一个值。
    const handleQuery = param => {
      if (!param) {
        param = {
          page: 1,
          size: pagination.pageSize
        };
      }
      loading.value = true;
      axios.get("/member/passenger/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        let data = response.data;
        if (data.success) {
          passengers.value = data.content.list;
          //设置分页控件的值
          pagination.current = param.page;
          pagination.total = data.content.total;
          console.log("1111", passengers)
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const handleTableChange = (pagination) => {
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };

    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.pageSize
      });
    });
    return {
      visible,
      showModal,
      handleOk,
      passenger,
      onFinish,
      onFinishFailed,
      // dataSource,
      pagination,
      columns,
      passengers,
      handleTableChange,
      handleQuery,
      loading
    };
  },
});
</script>