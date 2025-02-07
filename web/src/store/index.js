import { createStore } from 'vuex'

const MEMBER = "MEMBER"

export default createStore({
  state: {
    // {}避免空指针异常，有值就是缓存里的值
    member: window.SessionStorage.get(MEMBER) || {}
  },
  getters: {
  },
  mutations: {
    setMember(state,_member){
      state.member = _member;
      //属性有变动，更新，放到缓存里
      window.SessionStorage.set(MEMBER,_member)
    }
  },
  actions: {
  },
  modules: {
  }
})
