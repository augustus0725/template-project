
export default {

  namespace: 'example',

  state: {
    data: 100,
  },

  subscriptions: {
    setup({ dispatch, history }) {  // eslint-disable-line
    },
  },

  effects: {
    *getData({ payload }, { call, put }) {  // eslint-disable-line
      // 相当于sage里的订阅了  action: {type: "example/getData"}
      // 所以其他地方可以通过 dispatch, 来激活
      yield put({ type: 'example/clickData', payload: payload });
    },
  },

  reducers: {
    clickData(state, action) {
      console.log('State: ', state, ' action: ', action);
      return { ...state, data: action.payload };
    },
  },

};
