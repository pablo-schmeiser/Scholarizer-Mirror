import os
from os import listdir
from os.path import isfile, join


def main(dir_path, layer):
    path = dir_path.strip("\"")
    counter = 0
    files_lines = {}
    author_totals = {}

    folders = [join(path, f) for f in listdir(path) if not isfile(join(path, f))]
    files = [join(path, f) for f in listdir(path) if isfile(join(path, f))]

    for file in files:
        if file.endswith(".java"):
            with open(file, "r", encoding="utf-8") as f:
                lines = f.readlines()
                file_name_with_author = file
                counter = len(lines)
                for line in lines:
                    if "@author" in line:
                        author = line.split('@author ')[1].split(' ')[0]

                        author_totals[author] = author_totals.get(author, 0) + counter if author in author_totals else counter

                        file_name_with_author = f"{file}, written by: {author}"
                        break

            files_lines[file_name_with_author] = counter
            counter = 0

        if file.endswith((".js", ".css", ".ts", ".jsx", ".tsx")):
            author = "Frontend Team"
            with open(file, "r", encoding="utf-8") as f:
                counter = len(f.readlines())
                file_name_with_author = f"{file}, written by: {author}"

                author_totals[author] = author_totals.get(author, 0) + counter \
                    if author in author_totals else counter

            files_lines[file_name_with_author] = counter
            counter = 0

    for folder in folders:
        if (folder.endswith("src") and layer == 0) \
                or (folder.endswith(("reactapp", "reactapp v2")) and layer == 0) \
                or layer >= 1 and not folder.endswith(("node_modules", "test")):

            new_layer = layer + 1
            split_path = folder.split(os.sep)
            git_folder = os.sep.join(split_path[split_path.index("Scholarizer"):])
            print(f"going into layer: {new_layer} ({git_folder})")

            _, __ = main(folder, new_layer)
            files_lines.update(_)
            for k, v in __.items():
                author_totals[k] = author_totals[k] + v if k in author_totals else v

    return files_lines, author_totals


if __name__ == "__main__":
    result, authors = main(os.path.abspath(os.path.join(os.curdir, os.pardir)), 0)
    totalLines = 0
    assignedLines = 0

    for key, value in result.items():
        fileName = key.split(os.sep)[-1]
        print(f"-------------------\n{fileName}, contains: {value} lines")
        totalLines += value

    print("-------------------")

    for key, value in authors.items():
        assignedLines += value
        print(f"{key} has written: {value} lines")

    print(f"Unassigned: {totalLines - assignedLines} lines")

    print(f"-------------------\nTotal: {totalLines} lines")