import axios from 'axios';
import {Header} from './Header';

export async function getSkills() {
    const response = await axios.get<Header<Skill[]>>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/skill`);
    return response.data;
}

export async function getSkillsByMemberId(memberId: number) {
    const response = await axios.get<Header<Skill[]>>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/skill?memberId=${memberId}&size=300`);
    return response.data;
}

export async function getSkillById(id: number) {
    const response = await axios.get<Header<Skill>>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/skill/${id}`);
    return response.data;
}

export type Skill = {
    skill_id:        number;
    skill_name:      string;
    file_url:       string;
    file_origin_name: string;
    skill_level:     number;
    level:          number;
    member_id:       number;
}