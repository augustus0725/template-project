package main

import (
	"github.com/prometheus/client_golang/prometheus"
	"github.com/prometheus/client_golang/prometheus/promhttp"
	"log"
	"net/http"
)

func main() {
	reg := prometheus.NewRegistry()
	httpReqs := prometheus.NewCounterVec(
		prometheus.CounterOpts{
			Name: "http_requests_total",
			Help: "How many HTTP requests processed.",
		},
		[]string{"code", "method"},
	)
	reg.MustRegister(httpReqs)

	httpReqs.WithLabelValues("404", "POST").Add(100)

	http.Handle("/metrics", promhttp.HandlerFor(
		reg, promhttp.HandlerOpts{Registry: reg}))
	log.Fatal(http.ListenAndServe("0.0.0.0:8080", nil))
}
