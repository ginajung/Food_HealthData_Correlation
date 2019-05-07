public class DeathCode {
		
		/*
		 * column description
		 
		1. YEAR
		2. Cause of Death
		3. State
		4.StateCode
		5. Region
		6. AgeRange
		7. BenchMark
		8. Locality
		9. Population
		10. ExpectedDeath
		11. ExcessDeath
		12. PercentExcessDeath
		
		*/
		
		String year;
		String causeOfDeath;
		
		String stateCode;
		
		String locality;
		
		String percentExcessDeath;
		
		
		
		public DeathCode(String year, String causeOfDeath, String stateCode, String locality,
				String percentExcessDeath) {
			
			this.year = year;
			this.causeOfDeath = causeOfDeath;
			this.stateCode = stateCode;
			this.locality = locality;
			this.percentExcessDeath = percentExcessDeath;
		}



		public String getYear() {
			return year;
		}



		public void setYear(String year) {
			this.year = year;
		}



		public String getCauseOfDeath() {
			return causeOfDeath;
		}



		public void setCauseOfDeath(String causeOfDeath) {
			this.causeOfDeath = causeOfDeath;
		}



		public String getStateCode() {
			return stateCode;
		}



		public void setStateCode(String stateCode) {
			this.stateCode = stateCode;
		}



		public String getLocality() {
			return locality;
		}



		public void setLocality(String locality) {
			this.locality = locality;
		}



		public String getPercentExcessDeath() {
			return percentExcessDeath;
		}



		public void setPercentExcessDeath(String percentExcessDeath) {
			this.percentExcessDeath = percentExcessDeath;
		}
		
		
}
