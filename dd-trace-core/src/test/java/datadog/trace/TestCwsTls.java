
package datadog.trace;

import datadog.cws.tls.CwsTls;
import datadog.trace.api.DDId;

class TestCwsTls implements CwsTls {
    private DDId lastSpanId;
    private DDId lastTraceId;

    public void registerSpan(DDId spanId, DDId traceId) {
        lastSpanId = spanId;
        lastTraceId = traceId;
    }

    public DDId getSpanId() {
        return lastSpanId;
    }

    public DDId getTraceId() {
        return lastTraceId;
    }
}