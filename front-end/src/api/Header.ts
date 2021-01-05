export type Header<T> = {
    status: string; //api 응답코드
    data: T;
    pagination: Pagination;
}

export type Pagination = {
    totalPages: number;
    totalElements: number;
    currentPage: number;
    currentElements: number;
}