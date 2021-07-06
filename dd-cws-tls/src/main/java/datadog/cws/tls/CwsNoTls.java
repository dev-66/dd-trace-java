package datadog.cws.tls;

import datadog.trace.api.DDId;

public class CwsNoTls implements CwsTls {

  public void registerSpan(DDId spanId, DDId traceId) {}

  public DDId getSpanId() {
    return DDId.from(0);
  }

  public DDId getTraceId() {
    return DDId.from(0);
  }
}
