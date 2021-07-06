package datadog.cws.tls;

import datadog.trace.api.DDId;

public interface CwsTls {

  public void registerSpan(DDId spanId, DDId traceId);

  public DDId getSpanId();

  public DDId getTraceId();
}
