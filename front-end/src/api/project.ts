import axios from 'axios';
import {Header} from './Header';

export async function getProjects() {
    const response = await axios.get<Header<Project[]>>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/project`);
    return response.data;
}

export async function getProjectsByMemberId(memberId: number) {
    const response = await axios.get<Header<Project[]>>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/project?memberId=${memberId}&size=300&sort=level,asc`);
    return response.data;
}

export async function getProjectById(id: number) {
    const response = await axios.get<Header<Project>>(`http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com:8080/api/project/${id}`);
    return response.data;
}

export type Project =  {
    project_id:         number;
    project_title:      string;
    project_content:    string;
    project_post_script: string;
    file_url:          string;
    file_origin_name:    string;
    project_link:       string;
    level:             number;
    member_id:          number;
}