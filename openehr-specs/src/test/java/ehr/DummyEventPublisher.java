package ehr;

import ksch.instruction_state_machine.InstructionStateMachine;
import lombok.extern.java.Log;

@Log
public class DummyEventPublisher {

    public void publishStateChangeEvent(InstructionStateMachine.State newState) {
        log.info("The real implementation will trigger a system notification event here.");
    }
}
