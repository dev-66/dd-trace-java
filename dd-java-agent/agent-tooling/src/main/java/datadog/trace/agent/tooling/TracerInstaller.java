package datadog.trace.agent.tooling;

import datadog.communication.ddagent.SharedCommunicationObjects;
import datadog.trace.api.Config;
import datadog.trace.api.GlobalTracer;
import datadog.trace.bootstrap.instrumentation.api.AgentTracer;
import datadog.trace.ci.CITracer;
import datadog.trace.ci.CITracerFactory;
import datadog.trace.core.CoreTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TracerInstaller {
  private static final Logger log = LoggerFactory.getLogger(TracerInstaller.class);
  /** Register a global tracer if no global tracer is already registered. */
  public static synchronized void installGlobalTracer(
      SharedCommunicationObjects sharedCommunicationObjects) {
    if (Config.get().isCiVisibilityEnabled()) {
      if (!(GlobalTracer.get() instanceof CITracer)) {
        installGlobalTracer(
            CITracerFactory.createCITracer(Config.get(), sharedCommunicationObjects));
      } else {
        log.debug("GlobalTracer already registered.");
      }
    } else if (Config.get().isTraceEnabled()) {
      if (!(GlobalTracer.get() instanceof CoreTracer)) {
        installGlobalTracer(
            CoreTracer.builder().sharedCommunicationObjects(sharedCommunicationObjects).build());
      } else {
        log.debug("GlobalTracer already registered.");
      }
    } else {
      log.debug("Tracing is disabled, not installing GlobalTracer.");
    }
  }

  public static void installGlobalTracer(final AgentTracer.TracerAPI tracer) {
    try {
      GlobalTracer.registerIfAbsent(tracer);
      AgentTracer.registerIfAbsent(tracer);

      log.debug("Global tracer installed");
    } catch (final RuntimeException re) {
      log.warn("Failed to register tracer: {}", tracer, re);
    }
  }

  public static void forceInstallGlobalTracer(CoreTracer tracer) {
    try {
      log.warn("Overriding installed global tracer.  This is not intended for production use");

      GlobalTracer.forceRegister(tracer);
      AgentTracer.forceRegister(tracer);

      log.debug("Global tracer installed");
    } catch (final RuntimeException re) {
      log.warn("Failed to register tracer: {}", tracer, re);
    }
  }
}
