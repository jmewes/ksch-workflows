package ksch.orderentry;

import ksch.laboratory.LabCommands;
import ksch.laboratory.LabOrder;
import ksch.laboratory.LabOrderCode;
import ksch.laboratory.LabQueries;
import ksch.terminologies.LoincLabOrderValues;
import lombok.Getter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


public class LabOrderPanel extends Panel {

    @SpringBean
    private LabCommands labCommands;

    @SpringBean
    private LabQueries labQueries;

    private final UUID visitId;

    private WebMarkupContainer labRequests;

    public LabOrderPanel(UUID visitId) {
        super("labOrder");

        this.visitId = visitId;

        add(createAddLabOrderButton());
        add(createLabRequestsTable());
        add(createNoLabRequestsPresentMessage());
        add(new AddLabOrderForm());
    }

    private Button createAddLabOrderButton() {
        Button result = new Button("addLabOrderButton");
        result.setOutputMarkupId(true);
        result.setOutputMarkupPlaceholderTag(true);

        return result;
    }

    private WebMarkupContainer createLabRequestsTable() {
        WebMarkupContainer result = new WebMarkupContainer("labRequests");
        if (hasLabRequestsForCurrentVisit()) {
            result.setVisible(true);
        } else {
            result.setVisible(false);
        }

        List<LabOrderRow> labOrders = labQueries.getLabOrders(visitId).stream()
                .map(LabOrderRow::new)
                .collect(toList());

        ListView lv = new ListView<>("labOrders", labOrders) {
            @Override
            protected void populateItem(ListItem<LabOrderRow> item) {
                LabOrderRow rowData = item.getModelObject();

                item.add(new Label("loincNumber", rowData.getLoincNumber()));
                item.add(new Label("labTest", rowData.getLabTest()));
                item.add(new Label("status", rowData.getStatus()));
            }
        };

        result.add(lv);

        return result;
    }

    private WebMarkupContainer createNoLabRequestsPresentMessage() {
        WebMarkupContainer result = new WebMarkupContainer("noLabRequestsMessage");
        if (hasLabRequestsForCurrentVisit()) {
            result.setVisible(false);
        } else {
            result.setVisible(true);
        }
        return result;
    }

    private boolean hasLabRequestsForCurrentVisit() {
        return labQueries.getLabOrders(visitId).size() > 0;
    }

    @Getter
    class LabOrderRow implements Serializable {

        final String loincNumber;

        final String labTest;

        final String status;

        public LabOrderRow(LabOrder labOrder) {
            loincNumber = labOrder.getRequestedTest().toString();
            labTest = LoincLabOrderValues.get(loincNumber).getLongCommonName();
            status = labOrder.getStatus().toString();
        }
    }

    class AddLabOrderForm extends Form<Void> {

        public AddLabOrderForm() {
            super("addLabOrderForm");

            addTextField("loincNumber");
        }

        @Override
        protected void onSubmit() {
            String enteredLoincNumber = getValue("loincNumber");

            labCommands.requestExamination(visitId, new LabOrderCode(enteredLoincNumber));

            //setResponsePage(getPage()); // TODO Does this work as intended?
        }

        private void addTextField(String wicketId) {
            addTextField(wicketId, null);
        }

        private void addTextField(String wicketId, String initialValue) {
            TextField<String> textField = new TextField<>(wicketId, new Model<>(initialValue));
            add(textField);
        }

        private String getValue(String wicketId) {
            Object object = get(wicketId).getDefaultModel().getObject();
            return object != null ? object.toString() : null;
        }
    }
}
