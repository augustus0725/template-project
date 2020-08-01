package com.sabo.flink;

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.queryablestate.client.QueryableStateClient;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class WordCountTest {
    // @Test
    public void queryableTest() {
        try {
            QueryableStateClient client = new QueryableStateClient("192.168.1.102", 9069);

            ReducingStateDescriptor<Tuple2<String, Integer>> descriptor = new ReducingStateDescriptor<Tuple2<String, Integer>>(
                    "count",
                    new ReduceFunction<Tuple2<String, Integer>>() {
                        @Override
                        public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) throws Exception {
                            Tuple2<String, Integer> merge = new Tuple2<>(value1.f0, value1.f1 + value2.f1);
                            System.out.println(merge);
                            return merge;
                        }
                    },
                    TypeInformation.of(new TypeHint<Tuple2<String, Integer>>() {
                    })
            );

            CompletableFuture<ReducingState<Tuple2<String, Integer>>> resultFuture = client.getKvState(
                    JobID.fromHexString("377a20f3b364cb3b73bed48ca1c3ba1c"),
                    "queryCount",
                    "hello",
                    BasicTypeInfo.STRING_TYPE_INFO,
                    descriptor);

            resultFuture.thenAccept(response -> {
                try {
                    Tuple2<String, Integer> res = response.get();
                    System.out.println(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }
    }

}