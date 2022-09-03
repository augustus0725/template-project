
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
    *fetch({ payload }, { call, put }) {  // eslint-disable-line
      yield put({ type: 'save' });
    },
  },

  reducers: {
    clickData(state, action) {
      console.log('State: ', state, ' action: ', action);
      return { ...state, data: action.payload };
    },
  },

};
