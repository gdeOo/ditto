package org.jikesrvm.replay.modes;

import org.jikesrvm.classloader.NormalMethod;
import org.jikesrvm.classloader.RVMField;
import org.jikesrvm.classloader.TypeReference;
import org.jikesrvm.compilers.opt.driver.CompilerPhase;
import org.jikesrvm.replay.ReplayException;
import org.jikesrvm.replay.instrumentation.DittoWrapMemoryAccesses;
import org.jikesrvm.replay.instrumentation.MemoryAccessEntrypoints;
import org.jikesrvm.replay.instrumentation.SyncEntrypoints;
import org.jikesrvm.replay.instrumentation.ThreadLifetimeHandlers;
import org.jikesrvm.replay.sys.ditto.DittoRecorder;

/**
 * Ditto recorder mode.
 */
public final class DittoRecorderMode extends ReplayMode {

  @Override
  public void init() throws ReplayException {
    SyncEntrypoints.init(this);
    MemoryAccessEntrypoints.init(this);
    DittoRecorder.init();
  }

  @Override
  public void terminate() throws ReplayException {
    DittoRecorder.terminate();
  }

  @Override
  public ThreadLifetimeHandlers[] getThreadLifetimeHandlers() {
    return new ThreadLifetimeHandlers[] {
      DittoRecorder.INSTANCE
    };
  }

  @Override
  public boolean isRecorder() {
    return true;
  }

  @Override
  public boolean wrapsSynchronization() {
    return true;
  }

  @Override
  public boolean wrapsMemoryAccesses() {
    return true;
  }

  @Override
  public boolean trapsExits() {
    return true;
  }

  ///
  /// Synchronization entrypoints
  ///

  @Override
  public boolean hasSyncEntrypoints() {
    return true;
  }

  @Override
  public NormalMethod genericLockMethod() {
    return SyncEntrypoints.DittoRecorderEntrypoints.GENERIC_LOCK;
  }

  @Override
  public NormalMethod inlineLockMethod() {
    return SyncEntrypoints.DittoRecorderEntrypoints.INLINE_LOCK;
  }

  @Override
  public void runtimeGenericLock(Object o) {
    DittoRecorder.genericLock(o);
  }

  ///
  /// Memory access entrypoints
  ///

  @Override
  public boolean hasLoadStoreStyleEntrypoints() {
    return true;
  }

  @Override
  public NormalMethod getBeforeStaticLoadMethod() {
    return MemoryAccessEntrypoints.DittoRecorderEntrypoints.BEFORE_STATIC_LOAD;
  }

  @Override
  public NormalMethod getBeforeInstanceLoadMethod() {
    return MemoryAccessEntrypoints.DittoRecorderEntrypoints.BEFORE_INSTANCE_LOAD;
  }

  @Override
  public NormalMethod getAfterLoadMethod() {
    return MemoryAccessEntrypoints.DittoRecorderEntrypoints.AFTER_LOAD;
  }

  @Override
  public NormalMethod getBeforeStaticStoreMethod() {
    return MemoryAccessEntrypoints.DittoRecorderEntrypoints.BEFORE_STATIC_STORE;
  }

  @Override
  public NormalMethod getBeforeInstanceStoreMethod() {
    return MemoryAccessEntrypoints.DittoRecorderEntrypoints.BEFORE_INSTANCE_STORE;
  }

  @Override
  public NormalMethod getAfterStoreMethod() {
    return MemoryAccessEntrypoints.DittoRecorderEntrypoints.AFTER_STORE;
  }

  @Override
  public RVMField getThreadStateField() {
    return MemoryAccessEntrypoints.DittoRecorderEntrypoints.THREAD_STATE;
  }

  @Override
  public TypeReference getThreadStateType() {
    return MemoryAccessEntrypoints.DittoRecorderEntrypoints.THREAD_STATE_TYPE;
  }

  ///
  /// Compiler phases
  ///

  @Override
  public CompilerPhase getWrapMemoryAccessesCompilerPhase() {
    return new DittoWrapMemoryAccesses();
  }
}
