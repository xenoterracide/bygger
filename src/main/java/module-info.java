module com.xenoterracide.bygger {
  requires java.compiler;
  requires com.google.auto.service;
  requires org.apache.commons.lang3;
  exports com.xenoterracide.bygger.annotations;
  exports com.xenoterracide.bygger.processor to java.compiler;
}
