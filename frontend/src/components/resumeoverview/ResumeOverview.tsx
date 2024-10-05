import PersonalInfo from "./PersonalInfo";
import TechStack from "./TechStack";
import GoalCompany from "./GoalCompany";
import ETC from "./ETC";

function ResumeOverview() {
  return (
    <div className="w-[534px] h-auto  bg-white p-4 z-1">
      {/* Personal Info Section */}
      <PersonalInfo />
      {/* Tech Stack Section */}
      <TechStack />
      {/* Goal Company Section */}
      <GoalCompany />
      {/* ETC Section */}
      <ETC />
    </div>
  );
}

export default ResumeOverview;
