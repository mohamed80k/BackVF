package com.entity.type;

public enum ExportType {

	state_projects("state_projects"), state_tasks("state_tasks"), followed_projects(
			"followed_projects"), articles_template("articles_template"), tiers_template(
					"tiers_template"), followed_projects_daily(
							"followed_projects_daily"), followed_projects_by_commercial(
									"followed_projects_by_commercial"), customers_by_commercial("customers_by_commercial"
											),state_projects_by_states("state_projects_by_states"), states_template("states_template"
													), projects_template("projects_template"), lotissements_template("lotissements_template"
															), pointOfSales_template("pointOfSales_template"), customers_accounts("customers_accounts"),
															event_visitors("event_visitors");

	private String value;

	private ExportType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
