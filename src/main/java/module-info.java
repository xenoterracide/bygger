module com.xenoterracide.bygger {
  requires java.compiler;
  requires com.google.auto.service;
  exports com.xenoterracide.bygger.annotations;
  exports com.xenoterracide.bygger.processor to java.compiler;
}
